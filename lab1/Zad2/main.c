//
//  main.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include <stdlib.h>
#include "Unary_Function.h"
#include "Linear.h"
#include "Square.h"

int main()
{
    Unary_Function* f1 = malloc(sizeof(Square));
    initSquare((Square *)f1, -2, 2);
    tabulate(f1);
    
    Unary_Function* f2 = malloc(sizeof(Linear));
    initLinear((Linear *)f2, -2, 2, 5, -2);
    tabulate(f2);
    
    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
    PTRFUND negative_value= (PTRFUND)f2->table[1];
    printf("neg_val f2(1) = %lf\n", negative_value(f2, 1.0));
    free(f1);
    free(f2);
    return 0;
}
