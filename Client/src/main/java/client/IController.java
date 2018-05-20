package client;

import service.IService;

public interface IController {
    void initialize(StageManager stageManager, IService service);
}
