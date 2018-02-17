//
//  main.c
//  lab3
//
//  Created by Filip Gulan on 11/05/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include "myfactory.h"
#include "animal.h"

#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

// parrots and tigers defined in respective dynamic libraries

// animalPrintGreeting and animalPrintMenu similar as in lab 1
void animalPrintGreeting(Animal *animal) {
    printf("%s pozdravlja: %s\n", animal->vtable[0](animal), animal->vtable[1]());
}

void animalPrintMenu(Animal *animal) {
    printf("%s voli: %s\n", animal->vtable[0](animal), animal->vtable[2]());
}

int main(void){
    Animal* p1=(Animal *)myfactory("parrot", "Modrobradi");
    Animal* p2=(Animal *)myfactory("tiger", "Straško");
    if (!p1 || !p2){
        printf("Creation of plug-in objects failed.\n");
        exit(1);
    }
    
    animalPrintGreeting(p1);//"Sto mu gromova!"
    animalPrintGreeting(p2);//"Mijau!"

    animalPrintMenu(p1);//"brazilske orahe"
    animalPrintMenu(p2);//"mlako mlijeko"
    
    free(p1); free(p2);
    //dlclose(dlHandler);
}
