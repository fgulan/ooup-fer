//
//  Linear.h
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#ifndef Linear_h
#define Linear_h

#include "Unary_Function.h"

typedef struct {
    PTRFUN *table;
    int lower_bound;
    int upper_bound;
    double a;
    double b;
} Linear;

// Constructor
void initLinear(Linear *self, int lb, int ub, double a, double b);

#endif /* Linear_h */
