Node types
=====================================

If you want to dictate what kind of attributes and what kind of child nodes are allowed for your node, you will need to 
use a node type. You can compare node type declaration to class declaration in java. The node type declarations provide 
you with certain aspects of type safety. For example the exception (which one ???) will be thrown if you try to add a 
child node to a node that should not have children.

Each node has exactly one primary type and zero or more mixin types. Again, to use the analogy with java, primary type 
is like a class name, and mixin types are like interfaces this class implements. The primary type is specified when you 
create a new node and cannot be changed(???) after that. Mixin types can be added on the fly void 
```addMixin(String mixinName)``` if you need to introduce an additional characteristic to the node, for example 
'marking' the node as versionable or shareable.

The node type definitions are stored in a central system wide registry. We start with how you can define node types in 
your registry.

CRX Documentation
http://docs.adobe.com/docs/en/crx/current/developing/data_modeling.html
