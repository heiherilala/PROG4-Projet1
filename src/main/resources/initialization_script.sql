INSERT INTO users
(username, "password", enabled)
VALUES('admin', '$2a$12$Bm2EgKw2WZ5T4lpagVi1iOxWZmla76Z0C0jcLCP4q7v4hDsZ2heMS', true) ON CONFLICT DO NOTHING;

INSERT INTO company
(id, company_name, company_description, slogan, address, contact_email, nif, stat, rcs, logo)
VALUES('1', 'eraf', 'entreprise de BTP', 'De bon fondation pour une bon batiment', 'Ambohitrarahaba', 'eraf@moov.mg', '1234', '31231', '321313', NULL);
