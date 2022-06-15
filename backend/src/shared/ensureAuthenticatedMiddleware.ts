import {
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { Request, Response, NextFunction } from 'express';
import { verify } from 'jsonwebtoken';
import auth from '../config/auth';

interface TokenPayload {
  iat: number;
  exp: number;
  sub: string;
  id: string;
  email: string;
  name: string;
}

export default function ensureAuthenticatedMiddleware(
  req: Request,
  res: Response,
  next: NextFunction,
): void {
  try {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
      throw new NotFoundException('JWT is missing');
    }

    const [, token] = authHeader.split(' ');

    const decoded = verify(token, auth.secret);
    const { id, email, name } = decoded as TokenPayload;

    req.user = {
      id,
      email,
      name,
    };

    return next();
  } catch (err) {
    throw new UnauthorizedException('Token JWT inválido');
  }
}
