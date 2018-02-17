//
//  parrot.c
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
} Parrot;

char const *name(void *this) {
    return ((Parrot *)this)->name;
}

char const *greet() {
    return "Sto mu gromova!";
}

char const *menu() {
    return "brazilske orahe";
}

PTRFUN ParrotVTable[3] = {
    (PTRFUN) name,
    (PTRFUN) greet,
    (PTRFUN) menu
};

void initParrot(Parrot *this, char const *name) {
    this->vtable = ParrotVTable;
    this->name = name;
}

void *create(char const *name) {
    Parrot *parrot = malloc(sizeof(Parrot));
    initParrot(parrot, name);
    return parrot;
}