INSERT INTO country (id, name, acronym, phone_prefix)
VALUES (
    'b4f5e885-8c4e-4c8a-9d77-f1a2d76a8f91',
    'Mexico',
    'MX',
    '+52'
);

INSERT INTO companies (
    id,
    created_at,
    deleted,
    updated_at,
    created_user_id,
    updated_user_id,
    code,
    name,
    logo_url,
    time_zone,
    token_duration
) VALUES (
    'db0c37a1-8dd5-4af9-967c-44b4fb1ed62f',
    '2025-12-04 08:59:13.046245',
    FALSE,
    '2025-12-04 08:59:13.046245',
    NULL,
    NULL,
    '777',
    'Company CRM',
    NULL,
    NULL,
    NULL
);



INSERT INTO users (
    id,
    image_url,
    full_name,
    email,
    password,
    role,
    company_id,
    created_user_id,
    updated_user_id,
    deleted
) VALUES (
    'bbbb1111-cccc-2222-dddd-333333333333',
    '',
    'Alice Johnson',
    'user@example.com',
    '$2a$12$IhKdEWfcNXVWhbnqpSMTJun17BkIlleYHWX6hbrwNFD.7tXw4jb66',
    'USER',
    'db0c37a1-8dd5-4af9-967c-44b4fb1ed62f',
    NULL,
    NULL,
    FALSE
);



INSERT INTO users (
    id,
    image_url,
    full_name,
    email,
    password,
    role,
    company_id,
    created_user_id,
    updated_user_id,
    deleted
) VALUES (
    'bbbb1111-cccc-2222-dddd-333333333334',
    '',
    'ADMIN',
    'admin@example.com',
    '$2a$12$IhKdEWfcNXVWhbnqpSMTJun17BkIlleYHWX6hbrwNFD.7tXw4jb66',
    'ADMIN',
    'db0c37a1-8dd5-4af9-967c-44b4fb1ed62f',
    NULL,
    NULL,
    FALSE
);


INSERT INTO contacts (
    id,
    full_name,
    email,
    phone,
    country_id,
    funnel_status,
    user_id,
    active,
    company_id,
    created_at,
    updated_at,
	deleted
) VALUES (
    'a1b2c3d4-e5f6-7890-abcd-1234567890ef', -- id
    'John Doe',                             -- full_name
    'speralmoreno@gmail.com',                 -- email
    '1234567890',                           -- phone
    NULL,                                   -- country_id
    'NEW',                             -- funnel_status (enum)
    'bbbb1111-cccc-2222-dddd-333333333333', -- user_id
    TRUE,                                   -- active
    'db0c37a1-8dd5-4af9-967c-44b4fb1ed62f', -- company_id (from CompanyEntity)
    NOW(),                                  -- created_at
    NOW(),                                  -- updated_at
	FALSE
);

