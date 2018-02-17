//
//  Unary_Function.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include "Unary_Function.h"

PTRFUN unaryTable[2] = {NULL, (PTRFUN)negative_value_at};

void initUnaryFunction(Unary_Function *self, int lb, int ub)
{
    self->table = unaryTable;
    self->lower_bound = lb;
    self->upper_bound = ub;
}

double negative_value_at(Unary_Function *self, double x)
{
    return - ((PTRFUND)self->table[0])(self, x);
}

void tabulate(Unary_Function *self)
{
    PTRFUND value_at = (PTRFUND)self->table[0];
    for (int x = self->lower_bound; x <= self->upper_bound; x++) {
        printf("f(%d)=%lf\n", x, value_at(self, x));
    }
}

bool same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance)
{
    if(f1->lower_bound != f2->lower_bound) {
        return false;
    }
    
    if(f1->upper_bound != f2->upper_bound) {
        return false;
    }
    
    PTRFUND value_at_f1 = (PTRFUND)f1->table[0];
    PTRFUND value_at_f2 = (PTRFUND)f2->table[0];
    for(int x = f1->lower_bound; x <= f1->upper_bound; x++) {
        double delta = value_at_f1(f1, x) - value_at_f2(f2, x);
        if(delta < 0) {
            delta = -delta;
        }
        if(delta > tolerance) {
            return false;
        }
    }
    return true;
}