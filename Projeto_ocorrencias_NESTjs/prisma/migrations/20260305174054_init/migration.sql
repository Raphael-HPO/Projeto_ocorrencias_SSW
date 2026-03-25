/*
  Warnings:

  - A unique constraint covering the columns `[key]` on the table `ocorrencia` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateIndex
CREATE UNIQUE INDEX "ocorrencia_key_key" ON "ocorrencia"("key");
