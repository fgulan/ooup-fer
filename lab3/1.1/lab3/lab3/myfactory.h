//
//  myfactory.h
//  lab3
//
//  Created by Filip Gulan on 11/05/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef myfactory_h
#define myfactory_h

#include <stdio.h>

static void *dlHandler;

void* myfactory(char const* libname, char const* ctorarg);

#endif /* myfactory_h */
