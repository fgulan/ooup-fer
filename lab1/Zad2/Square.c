//
//  Square.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include "Square.h"

double value_at_square(Square *self, double x)
{
    return x * x;
}

PTRFUN squareTable[2] = {(PTRFUN)value_at_square, (PTRFUN)negative_value_at};

void initSquare(Square *self, int lb, int ub)
{
    initUnaryFunction((Unary_Function *)self, lb, ub);
    self->table = squareTable;
}
