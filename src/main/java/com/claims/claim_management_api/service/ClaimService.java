package com.claims.claim_management_api.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.claims.claim_management_api.model.Claims;

@Service
public class ClaimService {

    private final Map<Long, Claims> claims =
            new ConcurrentHashMap<>();

    private final AtomicLong idGenerator =
            new AtomicLong(100);

    public Claims createClaim(Claims claim) {
        Long id = idGenerator.incrementAndGet();

        Claims saved = new Claims(
                id,
                claim.policyNumber(),
                claim.claimantName(),
                claim.claimAmount(),
                claim.claimType(),
                "SUBMITTED"
        );

        claims.put(id, saved);
        return saved;
    }

    public List<Claims> getAllClaims() {
        //return List.copyOf(claims.values());
        return Arrays.asList(new Claims(123L, "001", "John Doe", BigDecimal.valueOf(1000.00), "auto", "inprogress")
                        , new Claims(124L, "002", "Jane Smith", BigDecimal.valueOf(2000.00), "home", "submitted")
                        , new Claims(125L, "003", "Bob Johnson", BigDecimal.valueOf(1500.00), "health", "approved"));
    }

    public Claims getClaimById(Long id) {
        return claims.get(id);
    }

    public Claims updateClaim(Long id, Claims updatedClaim) {
        Claims existing = claims.get(id);

        if (existing == null) {
            return null;
        }

        Claims updated = new Claims(
                id,
                updatedClaim.policyNumber(),
                updatedClaim.claimantName(),
                updatedClaim.claimAmount(),
                updatedClaim.claimType(),
                existing.status()
        );

        claims.put(id, updated);
        return updated;
    }

    public Claims updateStatus(Long id, String status) {
        Claims existing = claims.get(id);

        if (existing == null) {
            return null;
        }

        Claims updated = new Claims(
                existing.claimId(),
                existing.policyNumber(),
                existing.claimantName(),
                existing.claimAmount(),
                existing.claimType(),
                status
        );

        claims.put(id, updated);
        return updated;
    }

    public boolean deleteClaim(Long id) {
        return claims.remove(id) != null;
    }
}
