ALTER TABLE domains
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN created_by VARCHAR(45),
    ADD COLUMN edited_at TIMESTAMP WITHOUT TIME ZONE,
    ADD COLUMN edited_by VARCHAR(45),
    ADD COLUMN owner VARCHAR(45);

UPDATE domains
SET created_at = CURRENT_TIMESTAMP,
    created_by = 'Onbekend',
    edited_at = CURRENT_TIMESTAMP,
    edited_by = 'Onbekend',
    owner = 'Onbekend';

ALTER TABLE domains
    ALTER COLUMN created_at SET NOT NULL,
    ALTER COLUMN created_by SET NOT NULL,
    ALTER COLUMN edited_at SET NOT NULL,
    ALTER COLUMN edited_by SET NOT NULL,
    ALTER COLUMN owner SET NOT NULL;