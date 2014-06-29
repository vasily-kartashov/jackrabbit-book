The complete grammar for CND ins Backus–Naur form is:

```bnf
cnd ::= { ns_mapping | node_type_def }
ns_mapping ::= "<" prefix "=" uri ">"
prefix ::= string
uri ::= string
node_type_def ::= node_type_name [supertypes] [options] { property_def | child_node_def }
node_type_name ::= "[" string "]"
supertypes ::= ">" string_list
options ::= orderable_opt | mixin_opt | orderable_opt mixin_opt | mixin_opt orderable_opt
orderable_opt ::= "orderable" | "ord" | "o"
mixin_opt ::= "mixin" | "mix" | "m"
property_def ::= "-" property_name [property_type_decl] [default_values] [attributes] [value_constraints]
property_name ::= string
property_type_decl ::= "(" property_type ")"
property_type ::= "STRING"    | "String"    | "string"    |
                  "BINARY"    | "Binary"    | "binary"    |
                  "LONG"      | "Long"      | "long"      |
                  "DOUBLE"    | "Double"    | "double"    |
                  "BOOLEAN"   | "Boolean"   | "boolean"   |
                  "DATE"      | "Date"      | "date"      |
                  "NAME       | "Name       | "name"      |
                  "PATH"      | "Path"      | "path"      |
                  "REFERENCE" | "Reference" | "reference" | ??? WEAKREFERENCE ???
                  "UNDEFINED" | "Undefined" | "undefined" | "*"
default_values ::= "=" string_list
value_constraints ::= "<" string_list
child_node_def ::= "+" node_name [required_types] [default_type] [attributes]
node_name ::= string
required_types ::= "(" string_list ")"
default_type ::= "=" string
attributes ::= "primary"     | "pri" | "!" |
               "autocreated" | "aut" | "a" |
               "mandatory"   | "man" | "m" |
               "protected"   | "pro" | "p" |
               "multiple"    | "mul" | "*" |
               "COPY"        | "Copy"       | "copy"       |
               "VERSION"     | "Version"    | "version"    |
               "INITIALIZE"  | "Initialize" | "initialize" |
               "COMPUTE"     | "Compute"    | "compute"    |
               "IGNORE"      | "Ignore"     | "ignore"     |
               "ABORT"       | "Abort"      | "abort"
string_list ::= string { "," string }
string ::= quoted_string | unquoted_string
quoted_string :: = "'" unquoted_string "'"
unquoted_string ::= [A-Za-z0-9:_]+
```

So let's assume that we want to create a country node type with an string property name and a reference to capital city. 
A country can have multiple nodes of type city, each with string property name and long property population. All 
properties except the reference to capital city are required.

The CND notation would look like:

@Todo: write why you cannot create node types without namespace (no idea)
```
<ns = 'http://namespace.com/ns'>
<app = 'http://kartashov.com/jackrabbit-cookbook'>

[app:city] > nt:base, mix:referenceable
    - app:name (string) = "" mandatory autocreated
    - app:population (long) = "0" mandatory autocreated

[app:country] > nt:base
    orderable
    - app:name (string) = "" mandatory autocreated
    - app:capital (reference)
    + * (app:city) = app:city
```

To register the definitions you could run the following code

```java
File file = file = new File(getClass().getResource("/node-types.cnd").toURI());
NodeType[] nodeTypes = CndImporter.registerNodeTypes(new FileReader(file), session);

Node n0 = session.getRootNode().addNode("australia", "app:country");

assert n0.hasProperty("app:name");

Node n1 = n0.addNode("canberra", "app:city");

assert n1.hasProperty("app:name");
assert n1.hasProperty("app:population");

n0.setProperty("app:capital", n1);
```