//
//  Linear.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include "Linear.h"

double value_at_linear(Linear *self, double x)
{
    return (self->a) * x + self->b;
}

PTRFUN linearTable[2] = {(PTRFUN)value_at_linear, (PTRFUN)negative_value_at};

void initLinear(Linear *self, int lb, int ub, double a, double b)
{
    initUnaryFunction((Unary_Function *)self, lb, ub);
    self->table = linearTable;
    self->a = a;
    self->b = b;
}