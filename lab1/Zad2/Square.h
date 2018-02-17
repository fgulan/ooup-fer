//
//  Square.h
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#ifndef Square_h
#define Square_h

#include "Unary_Function.h"

typedef struct {
    PTRFUN *table;
    int lower_bound;
    int upper_bound;
} Square;

// Constructor
void initSquare(Square *self, int lb, int ub);

#endif /* Square_h */
