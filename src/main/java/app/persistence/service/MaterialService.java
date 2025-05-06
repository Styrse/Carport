package app.persistence.service;

import app.entities.products.materials.planks.Post;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;

import java.util.List;

public class MaterialService {

    public static List<Post> getAllPosts() throws DatabaseException {
        return MaterialMapper.getAllMaterials().stream().filter(Post.class::isInstance).map(Post.class::cast).toList();
    }
}
