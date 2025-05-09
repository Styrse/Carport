package app.service;

import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.exceptions.DatabaseException;
import app.persistence.mappers.CarportMapper;
import app.persistence.mappers.MaterialMapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CarportService {
    public static void saveCarport(Carport carport, Map<MaterialRole, Integer> materialsId) throws DatabaseException, SQLException {
        Map<MaterialRole, Material> materialMap = new HashMap<>();

        for (Map.Entry<MaterialRole, Integer> entry : materialsId.entrySet()) {
            MaterialRole role = entry.getKey();
            int materialId = entry.getValue();

            Material material = MaterialMapper.getMaterialById(materialId);
            materialMap.put(role, material);
        }

        carport.setMaterialMap(materialMap);

        CarportMapper.createCarport(carport);
    }

}
