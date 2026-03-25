import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { PrismaModule } from './prisma/prisma.module';
import { OcorrenciaModule } from './ocorrencia/ocorrencia.module';

@Module({
  imports: [PrismaModule, OcorrenciaModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
