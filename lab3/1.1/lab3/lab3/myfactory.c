//
//  myfactory.c
//  lab3
//
//  Created by Filip Gulan on 11/05/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include "myfactory.h"
#include "animal.h"
#include <stdlib.h>
#include <dlfcn.h>

typedef Animal* (*PTRFUN_AC)(char const*);

void *myfactory(char const* libname, char const* ctorarg) {
    char *error;
    PTRFUN_AC create;

    char *filename;
    int size = asprintf(&filename, "./%s.so", libname);

    if (size == -1) {
        printf("Unexpected error!");
        return NULL;
    }

    dlHandler = dlopen(filename, RTLD_LAZY);
    free(filename);
    if (!dlHandler) {
        printf("Unable to open given lib - %s. Error : %s\n", libname, dlerror());
        return NULL;
    }

    error = dlerror();
    if (error) {
        printf("Unexpected error - %s\n", error);
        return NULL;
    }

    create = dlsym(dlHandler, "create");
    error = dlerror();
    if (error) {
        printf("Unable to create lib - %s\n", error);
        return NULL;
    }

    return create(ctorarg);
}