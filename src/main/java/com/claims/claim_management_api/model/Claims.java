package com.claims.claim_management_api.model;

import java.math.BigDecimal;

public record Claims(
        Long claimId,
        String policyNumber,
        String claimantName,
        BigDecimal claimAmount,
        String claimType,
        String status
) {
}
