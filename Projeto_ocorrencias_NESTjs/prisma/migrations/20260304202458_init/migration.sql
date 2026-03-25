/*
  Warnings:

  - You are about to drop the `user` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropTable
DROP TABLE "user";

-- CreateTable
CREATE TABLE "ocorrencia" (
    "id" SERIAL NOT NULL,
    "key" VARCHAR(100) NOT NULL,
    "retorno" VARCHAR(100) NOT NULL DEFAULT 'aguardando',

    CONSTRAINT "ocorrencia_pkey" PRIMARY KEY ("id")
);
