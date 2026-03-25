-- CreateTable
CREATE TABLE "user" (
    "id" SERIAL NOT NULL,
    "key" VARCHAR(100) NOT NULL,
    "retorno" VARCHAR(100) NOT NULL,

    CONSTRAINT "user_pkey" PRIMARY KEY ("id")
);
