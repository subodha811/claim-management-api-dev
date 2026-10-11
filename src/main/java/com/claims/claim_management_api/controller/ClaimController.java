package com.claims.claim_management_api.controller;

import com.claims.claim_management_api.model.Claims;
import com.claims.claim_management_api.service.ClaimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    public ResponseEntity<Claims> createClaim(
            @RequestBody Claims claim) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(claimService.createClaim(claim));
    }

    @GetMapping
    public List<Claims> getAllClaims() {
        return claimService.getAllClaims();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claims> getClaimById(
            @PathVariable Long id) {
        Claims claim = claimService.getClaimById(id);

        return claim == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(claim);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Claims> updateClaim(
            @PathVariable Long id,
            @RequestBody Claims claim) {
        Claims updated = claimService.updateClaim(id, claim);

        return updated == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Claims> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");

        if (status == null || status.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Claims updated = claimService.updateStatus(id, status);

        return updated == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(
            @PathVariable Long id) {
        return claimService.deleteClaim(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
