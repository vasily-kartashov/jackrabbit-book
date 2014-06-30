Inheritance
==================================

Primary node types are arranged in an inheritance hierarchy. Every primary node type must be the subtype of at least one 
existing node type. The built-in node type nt:base serves as the root of this hierarchy. Jackrabbit supports multiple 
inheritance of node types so node types can have more than one supertype.

Mixin node types do not have to have supertypes.

The JSR 170 specification and the current public review draft of the JSR 283 specification (section 4.7.7) leave it up 
to the implementation whether e.g. the orderable child nodes setting is inherited from supertypes. Inheritance 
semantics, especially with multiple inheritance, are non-trivial at best and up to a certain degree arbitrary. 
Jackrabbit therefore, in compliance with the spec, doesn't support inheritance of node type attributes such as 
orderable.

http://jackrabbit.apache.org/node-types.html

@todo: describe inheritance and create simple program showing how it works