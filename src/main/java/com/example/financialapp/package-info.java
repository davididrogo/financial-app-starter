/**
 * Package layout (Hexagonal Architecture):
 * - domain: Entities + value objects + domain services (pure Java)
 * - application: Use cases (ports in) coordinating domain and persistence
 * - infrastructure: adapters (web, persistence, security, config)
 * - shared: cross-cutting concerns (error handling, mapper)
 */
package com.example.financialapp;
