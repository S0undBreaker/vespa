// Copyright 2016 Yahoo Inc. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.

#include <vespa/fastos/fastos.h>
#include "tensor_attribute_executor.h"
#include <vespa/searchlib/tensor/tensor_attribute.h>

namespace search {
namespace features {

TensorAttributeExecutor::
TensorAttributeExecutor(const search::attribute::TensorAttribute *attribute)
    : _attribute(attribute),
      _tensor(),
      _emptyTensor(std::make_unique<vespalib::eval::TensorValue>(attribute->getEmptyTensor()))
{
}

void
TensorAttributeExecutor::execute(fef::MatchData &data)
{
    auto tensor = _attribute->getTensor(data.getDocId());
    if (!tensor) {
        *data.resolve_object_feature(outputs()[0]) = *_emptyTensor;
        return;
    }
    _tensor = std::make_unique<vespalib::eval::TensorValue>(std::move(tensor));
    *data.resolve_object_feature(outputs()[0]) = *_tensor;
}

} // namespace features
} // namespace search