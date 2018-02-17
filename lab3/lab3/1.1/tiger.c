//
//  tiger.c
//  lab3
//
//  Created by Filip Gulan on 11/05/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct {
    PTRFUN *vtable;
    char const* name;
} Tiger;

char const *name(void *this) {
    return ((Tiger *)this)->name;
}

char const *greet() {
    return "Mijau!";
}

char const *menu() {
    return "mlako mlijeko";
}

PTRFUN TigerVTable[3] = {
    (PTRFUN) name,
    (PTRFUN) greet,
    (PTRFUN) menu
};

void initTiger(Tiger *this, char const *name) {
    this->vtable = TigerVTable;
    this->name = name;
}

void *create(char const *name) {
    Tiger *tiger = malloc(sizeof(Tiger));
    initTiger(tiger, name);
    return tiger;
}