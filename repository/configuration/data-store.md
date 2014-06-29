Data store 
========================================================

The data store is optionally used to store large binary values. Normally all node and property data is stored in a 
persistence manager, but for large binaries such as files special treatment can improve performance and reduce disk 
usage.

The main features of the data store are:

- Space saving: only one copy per unique object it kept
- Fast copy: only the identifier is copied
- Storing and reading does not block others
- Multiple repositories can use the same data store
- Objects in the data store are immutable
- Garbage collection is used to purge unused objects
- Hot backup is supported
- Clustering: all cluster nodes use the same data store

@todo take FileDataStore, DbDataStore, CachingDataStore, S3DataStore, InMemoryDataStore, DerbyDataStore and describe 
their settings.