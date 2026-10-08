-- Connection details move from env vars to the database. They stay nullable so existing
-- environments survive the upgrade; the API requires them and the UI enforces filling them in.
-- Audit columns have defaults so the previous app version can still insert rows during a rollout.
ALTER TABLE environments
    ADD COLUMN url                VARCHAR(2048),
    ADD COLUMN username           VARCHAR(255),
    ADD COLUMN password_encrypted VARCHAR(1024),
    ADD COLUMN active             BOOLEAN                  NOT NULL DEFAULT TRUE,
    ADD COLUMN internal           BOOLEAN                  NOT NULL DEFAULT FALSE,
    ADD COLUMN created_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN created_by         VARCHAR(255)             NOT NULL DEFAULT 'Onbekend',
    ADD COLUMN updated_by         VARCHAR(255)             NOT NULL DEFAULT 'Onbekend';

-- Hidden environment used by the unit test runner. Its connection details still come from env vars.
INSERT INTO environments (name, internal, active)
SELECT 'Test', TRUE, TRUE
WHERE NOT EXISTS (SELECT 1 FROM environments WHERE internal);

-- Deleting an environment keeps its deployments visible by unlinking them.
-- Legacy (Hibernate-created) schemas may use a generated FK name, so drop whatever FK exists.
DO $$
DECLARE
    constraint_to_drop RECORD;
BEGIN
    FOR constraint_to_drop IN
        SELECT con.conname
        FROM pg_constraint con
        WHERE con.conrelid = 'public.deployments'::regclass
          AND con.confrelid = 'public.environments'::regclass
          AND con.contype = 'f'
    LOOP
        EXECUTE format('ALTER TABLE deployments DROP CONSTRAINT %I', constraint_to_drop.conname);
    END LOOP;
END $$;

ALTER TABLE deployments
    ALTER COLUMN environment_id DROP NOT NULL,
    ADD CONSTRAINT fk_deployments_environment FOREIGN KEY (environment_id)
        REFERENCES environments (id) ON DELETE SET NULL;
