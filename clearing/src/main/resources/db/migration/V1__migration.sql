SET TIME ZONE 'UTC';

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS processable_transactions (
   id UUID UNIQUE PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
   transaction_data JSONB NOT NULL,
   pricing_details JSONB,
   transaction_id TEXT NOT NULL,
   status TEXT NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT now(),
   owner TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS processable_receivables (
   id UUID UNIQUE PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
   transaction_data JSONB NOT NULL,
   pricing_details JSONB,
   transaction_id TEXT NOT NULL,
   status TEXT NOT NULL,
   receivables JSONB,
   created_at TIMESTAMP NOT NULL DEFAULT now(),
   owner TEXT NOT NULL
);
