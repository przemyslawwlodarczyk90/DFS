import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface Cabinet {
    // zwraca dowolny element o podanej nazwie
    Optional<Folder>
    findFolderByName(String name);

    // zwraca wszystkie foldery podanego rozmiaru SMALL/MEDIUM/LARGE
    List<Folder> findFoldersBySize(String size);

    //zwraca liczbę wszystkich obiektów tworzących strukturę
    int count();
}


public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {

        if (name == null){
            return Optional.empty();
        }

        List<Folder> processed1 = allFolders(folders);


        for (Folder children : processed1) {
            if (name.equals(children.getName())) {
                return Optional.of(children);
            }
        }
        return Optional.empty();
    }


    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> allBySize = new ArrayList<>();
        List<Folder> processed2 = allFolders(folders);

        if (size == null){
            return allBySize;
        }

            for (Folder children : processed2) {
                if (size.equals(children.getSize())) {
                    allBySize.add(children);
                }
        }

        return allBySize;
    }

    @Override
    public int count() {
        return allFolders(folders).size();
    }

    private List<Folder> allFolders(List<Folder> processedFolders) {
        List<Folder> fullList = new ArrayList<>();

        if (processedFolders == null) {
            return fullList;
        }

        for (Folder folder1 : processedFolders) {
            fullList.add(folder1);
            if (folder1 instanceof MultiFolder) {
                List<Folder> internal = ((MultiFolder) folder1).getFolders();
                fullList.addAll(allFolders(internal));
            }
        }
        return fullList;
    }

}



interface Folder {
    String getName();

    String getSize();
}



interface MultiFolder extends Folder {
    List<Folder> getFolders();
}
