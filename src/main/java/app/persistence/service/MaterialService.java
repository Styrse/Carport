package app.persistence.service;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;

import java.util.List;

public class MaterialService {

    public static List<Post> getAllPosts() throws DatabaseException {
        return MaterialMapper.getAllMaterials().stream().filter(Post.class::isInstance).map(Post.class::cast).toList();
    }

    public static List<Beam> getAllBeams() throws DatabaseException {
        return MaterialMapper.getAllMaterials().stream().filter(Beam.class::isInstance).map(Beam.class::cast).toList();
    }

    public static List<Rafter> getAllRafters() throws DatabaseException {
        return MaterialMapper.getAllMaterials().stream().filter(Rafter.class::isInstance).map(Rafter.class::cast).toList();
    }

    public static List<Fascia> getAllFascias() throws DatabaseException {
        return MaterialMapper.getAllMaterials().stream().filter(Fascia.class::isInstance).map(Fascia.class::cast).toList();
    }

    public static List<RoofCover> getAllRoofCovers() throws DatabaseException {
        return MaterialMapper.getAllMaterials().stream().filter(RoofCover.class::isInstance).map(RoofCover.class::cast).toList();
    }
}
