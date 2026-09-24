ALTER TABLE domains
    ADD COLUMN created_date TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN created_by VARCHAR(45),
    ADD COLUMN modified_date TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN modified_by VARCHAR(45),
    ADD COLUMN owner VARCHAR(45);

UPDATE domains
SET created_date = CURRENT_TIMESTAMP,
    created_by = 'Onbekend',
    modified_date = CURRENT_TIMESTAMP,
    modified_by = 'Onbekend',
    owner = 'Onbekend';

ALTER TABLE domains
    ALTER COLUMN created_date SET NOT NULL,
    ALTER COLUMN created_by SET NOT NULL,
    ALTER COLUMN modified_date SET NOT NULL,
    ALTER COLUMN modified_by SET NOT NULL,
    ALTER COLUMN owner SET NOT NULL;