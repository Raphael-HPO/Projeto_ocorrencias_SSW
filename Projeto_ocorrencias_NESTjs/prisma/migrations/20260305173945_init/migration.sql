/*
  Warnings:

  - The primary key for the `ocorrencia` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - You are about to drop the column `id` on the `ocorrencia` table. All the data in the column will be lost.

*/
-- AlterTable
ALTER TABLE "ocorrencia" DROP CONSTRAINT "ocorrencia_pkey",
DROP COLUMN "id",
ADD CONSTRAINT "ocorrencia_pkey" PRIMARY KEY ("key");
