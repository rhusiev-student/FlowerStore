package flower.store;

import org.springframework.data.jpa.repository.JpaRepository;

import flower.store.items.Flower;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {
}
