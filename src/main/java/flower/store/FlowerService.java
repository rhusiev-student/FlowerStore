package flower.store;

import flower.store.filters.SearchFilter;
import flower.store.items.Flower;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowerService {
    private FlowerRepository flowerRepository;

    @Autowired
    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public void add(Flower flower) { flowerRepository.save(flower); }

    public List<Flower> get() { return flowerRepository.findAll(); }

    public void remove(Flower flower) { flowerRepository.delete(flower); }

    public void removeById(int id) { flowerRepository.deleteById(id); }

    public List<Flower> search(SearchFilter filter) {
        List<Flower> flowers = flowerRepository.findAll();
        List<Flower> result = new ArrayList<>();
        for (Flower flower : flowers) {
            if (filter.matches(flower)) {
                result.add(flower);
            }
        }
        return result;
    }
}
