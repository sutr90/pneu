package pneu.tireForm;

import pneu.storage.StorageService;

import javax.inject.Inject;

public class TireFormController {
    @Inject
    private StorageService storageService;

    @Inject
    public void initialize() {
        //todo prefill info based on storageService status
    }
}
