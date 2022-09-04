package com.iafsd.killyourhabit.repository

import dagger.Component
import javax.inject.Singleton

// @Component makes Dagger create a graph of dependencies
// Scope annotations on a @Component interface informs Dagger that classes annotated
// with this annotation (i.e. @Singleton) are bound to the life of the graph and so
// the same instance of that type is provided every time the type is requested.
@Singleton
@Component
interface KYHApplicationComponent {

    // The return type  of functions inside the component interface is
    // what can be provided from the container
    //fun network(): NetworkModule //Retrofit
    fun firebase(): UserRepositoryImpl
}