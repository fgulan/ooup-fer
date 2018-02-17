//
//  animal.h
//  lab3
//
//  Created by Filip Gulan on 11/05/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef animal_h
#define animal_h

typedef char const* (*PTRFUN)();

typedef struct {
    PTRFUN* vtable;
    // vtable entries:
    // 0: char const* name(void* this);
    // 1: char const* greet();
    // 2: char const* menu();
} Animal;

#endif /* animal_h */
