//
//  Dog.h
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#ifndef Dog_h
#define Dog_h

#include "Animal.h"

// Constructor
void constructDog(Animal *dog, char *name);
Animal* createDog(char *name);

Animal getStackDogWithName(char *name);

#endif /* Dog_h */
