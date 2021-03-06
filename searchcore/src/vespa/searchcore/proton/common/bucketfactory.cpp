// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.

#include "bucketfactory.h"

using document::BucketId;
using document::DocumentId;
using storage::spi::Bucket;
using storage::spi::PartitionId;

namespace proton {

BucketId
BucketFactory::getBucketId(const DocumentId &docId)
{
    BucketId bId = docId.getGlobalId().convertToBucketId();
    bId.setUsedBits(getNumBucketBits());
    return bId;
}


Bucket
BucketFactory::getBucket(const DocumentId &docId)
{
    return Bucket(document::Bucket(document::BucketSpace::placeHolder(), getBucketId(docId)), PartitionId(0));
}

} // namespace proton
