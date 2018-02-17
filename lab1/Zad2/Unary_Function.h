//
//  Unary_Function.h
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#ifndef Unary_Function_h
#define Unary_Function_h

#include <stdio.h>
#include <stdbool.h>

typedef void* (*PTRFUN)(void);

typedef struct {
    PTRFUN *table;
    int lower_bound;
    int upper_bound;
} Unary_Function;

typedef double (*PTRFUND)(Unary_Function *, double);

// Constructor
void initUnaryFunction(Unary_Function *self, int lb, int ub);

// Public methods
void tabulate(Unary_Function *self);
double negative_value_at(Unary_Function *self, double x);
bool same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance);

#endif /* Unary_Function_h */
