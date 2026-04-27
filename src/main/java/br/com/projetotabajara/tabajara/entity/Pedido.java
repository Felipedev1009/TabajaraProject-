package br.com.projetotabajara.tabajara.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPedido;

    private LocalDate dataPedido;

    private double totalPedido;
    // outros atributos, construtores, getters e setters
    
    @ManyToOne
    @JoinColumn(name = "idUsuario_fk")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemDoPedido> itens;

    //Método para calcular o total do pedido
    public double calcularTotal() {
        double total = 0.0;
        if (itens != null) {
            for (ItemDoPedido item : itens) {
                total += item.getSubtotal() * item.getQuantidade();
            }
        }
        return total;
    }

    // metodo para atualizar o total do pedido
    public void atualizarTotal() {
        this.totalPedido = calcularTotal();
    }

}

