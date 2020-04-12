package console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import utils.Config;

public final class DeleteCacheCommand implements Command {

    @Override
    public String execute() {
        try {
            Files.delete(Paths.get(Config.CACHE_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

}