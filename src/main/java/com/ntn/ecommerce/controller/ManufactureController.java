package com.ntn.ecommerce.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.ntn.ecommerce.dto.request.ManufactureRequest;
import com.ntn.ecommerce.dto.response.ApiResponse;
import com.ntn.ecommerce.dto.response.ManufactureResponse;
import com.ntn.ecommerce.dto.response.PageResponse;
import com.ntn.ecommerce.service.ManufactureService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/manufactures")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ManufactureController {
    ManufactureService manufactureService;

    @PostMapping("/create")
    ApiResponse<ManufactureResponse> createManufacture(@RequestBody @Valid ManufactureRequest request) {
        return ApiResponse.<ManufactureResponse>builder()
                .result(manufactureService.createManufacture(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ManufactureResponse>> getAllManufactures() {
        return ApiResponse.<List<ManufactureResponse>>builder()
                .result(manufactureService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ManufactureResponse> getManufactureById(@PathVariable Long id) {
        return ApiResponse.<ManufactureResponse>builder()
                .result(manufactureService.getManufactureById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ManufactureResponse> updateManufacture(
            @PathVariable Long id, @RequestBody @Valid ManufactureRequest request) {
        return ApiResponse.<ManufactureResponse>builder()
                .result(manufactureService.updateManufacture(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteManufacture(@PathVariable Long id) {
        manufactureService.deleteManufacture(id);
        return ApiResponse.<Void>builder().message("Manufacture deleted").build();
    }

    @GetMapping("/pagination-sort")
    public ApiResponse<PageResponse<ManufactureResponse>> getAllPaginationSortManufacture(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        PageResponse<ManufactureResponse> manufacs =
                manufactureService.getManufactures(page, size, sortBy, sortDirection);
        return ApiResponse.<PageResponse<ManufactureResponse>>builder()
                .result(manufacs)
                .build();
    }
}
