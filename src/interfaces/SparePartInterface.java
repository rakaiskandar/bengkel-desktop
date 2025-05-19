/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;
import java.util.List;
import models.SparePart;

/**
 *
 * @author HP
 */
public interface SparePartInterface {
    SparePart getSparePartById(int id);
    List<SparePart> getAllSpareParts();
    boolean addSparePart(SparePart part);
    boolean updateSparePart(SparePart part);
    boolean deleteSparePart(int id);
}
