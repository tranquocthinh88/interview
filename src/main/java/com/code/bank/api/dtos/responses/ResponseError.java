package com.code.bank.api.dtos.responses;

import java.util.List;

public record ResponseError(int status, List<String> errors) implements Response {
}
