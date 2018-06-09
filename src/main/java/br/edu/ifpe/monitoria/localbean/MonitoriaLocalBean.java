package br.edu.ifpe.monitoria.localbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Aluno;
import br.edu.ifpe.monitoria.entidades.Edital;
import br.edu.ifpe.monitoria.entidades.Monitoria;
import br.edu.ifpe.monitoria.entidades.PlanoMonitoria;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@Stateless
@LocalBean
public class MonitoriaLocalBean
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	public List<Monitoria> consultaMonitoriasAvaliadas(){
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findAvaliadas", Monitoria.class).getResultList();

		return monitorias;
	}
	
	
	public List<Monitoria> consultaMonitorias()
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findAll", Monitoria.class).getResultList();

		return monitorias;
	}

	public List<Monitoria> consultaMonitoriaByProfessor(Long idProfessor)
	{
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByProfessor", Monitoria.class)
										  .setParameter("id", idProfessor).getResultList();

		return monitorias;
	}
	
	public boolean persisteMonitoria (@Valid @NotNull Monitoria monitoria)
	{
		em.persist(monitoria);

		return true;
	}
	
	public DelecaoRequestResult deletaMonitoria(Long id)
	{
		DelecaoRequestResult delecao = new DelecaoRequestResult();
		
		Monitoria monitoriaDeletada = em.createNamedQuery("Monitoria.findById", Monitoria.class).setParameter("id", id).getSingleResult();

		try {
			em.remove(monitoriaDeletada);
		} catch (Exception e) {
			delecao.errors.add("Problemas na remoção da entidade no banco de dados, contate o suporte.");
		}
				
		return delecao;
	}


	public Monitoria consultaMonitoriaByAluno(Aluno aluno, Edital edital) {
		Monitoria monitoria = null;
		try {
		 monitoria = em.createNamedQuery("Monitoria.findByAluno", Monitoria.class).
				setParameter("aluno", aluno).
				setParameter("edital", edital).
				getSingleResult();
		}catch (NoResultException e) {
			e.printStackTrace();
		}
		return monitoria;
	}


	public void atualizaMonitoria(Monitoria monitoria) {
		em.merge(monitoria);
	}


	public List<Monitoria> consultaMonitoriaByPlano(PlanoMonitoria plano) {
		List<Monitoria> monitorias = em.createNamedQuery("Monitoria.findByPlano", Monitoria.class)
				  .setParameter("plano", plano).getResultList();

		return monitorias;
	}


	public void salvarNotas(List<Monitoria> monitorias) {
		monitorias = verificarEmpates(monitorias);
		monitorias = ordenar(monitorias);
		monitorias = classificar(monitorias);
		for (Monitoria monitoria : monitorias) {
			em.merge(monitoria);
		}
	}

	public List<Monitoria> verificarEmpates(List<Monitoria> monitorias) {
		for (Monitoria m1 : monitorias) {
			boolean empate = false;
			for (Monitoria m2 : monitorias) {
				if(m1.getId().longValue() != m2.getId().longValue() &&
						m1.getMediaComponente() != null && m1.getNotaSelecao() != null &&
						m2.getMediaComponente() != null && m2.getNotaSelecao() != null) {
					if(m1.getNotaSelecao().doubleValue() == m2.getNotaSelecao().doubleValue() &&
							m1.getMediaComponente().doubleValue() == m2.getMediaComponente().doubleValue()) {
						empate=true;
						break;
					} 
				}
			}
			m1.setEmpatado(empate);
			if(empate && m1.getDesempate() == null) { 
				atualizarEmpatados(m1, monitorias);
			}
			else if(!empate){ 
				m1.setDesempate(null);
			}
		}
		return monitorias;
	}
	
	private void atualizarEmpatados(Monitoria m1, List<Monitoria> monitorias) {
		Integer desempate = 1;
		for (Monitoria monitoria : monitorias) {
			if(monitoria.getNotaSelecao() != null && monitoria.getMediaComponente() != null) {
				if(monitoria.getNotaSelecao().doubleValue() == m1.getNotaSelecao().doubleValue() &&
						monitoria.getMediaComponente().doubleValue() == m1.getMediaComponente().doubleValue()) {
					monitoria.setEmpatado(true);
					monitoria.setDesempate(desempate);
					desempate++;
				}
			}
		}
	}

	public List<Monitoria> ordenar(List<Monitoria> monitorias) {
		Collections.sort(monitorias, new Comparator<Monitoria>() {
			@Override
			public int compare(Monitoria m1, Monitoria m2) {
				
				if(!m1.isClassificado() && !m2.isClassificado()) {
					return 0;
				} else if (!m1.isClassificado()) {
					return 1;
				} else if (!m2.isClassificado()) {
					return -1;
				}
				
				if(m1.getNotaSelecao() != null && 
						m2.getNotaSelecao() != null && 
						m1.getMediaComponente() != null &&
						m2.getMediaComponente() != null) {
					if(m1.getNotaSelecao().doubleValue() > m2.getNotaSelecao().doubleValue()) {
						return -1;
					} else if (m1.getNotaSelecao().doubleValue() < m2.getNotaSelecao().doubleValue()) {
						return 1;
					} else if (m1.getNotaSelecao().doubleValue() == m2.getNotaSelecao().doubleValue()) {
						if(m1.getMediaComponente().doubleValue() > m2.getMediaComponente().doubleValue()) {
							return -1;
						} else if (m1.getMediaComponente().doubleValue() < m2.getMediaComponente().doubleValue()) {
							return 1;
						} else if (m1.getMediaComponente().doubleValue() == m2.getMediaComponente().doubleValue()) {
							if(m1.getDesempate() != null && m2.getDesempate() != null) {
								if(m1.getDesempate().intValue() > m2.getDesempate().intValue()) {
									return -1;
								} else if(m1.getDesempate().intValue() < m2.getDesempate().intValue()) {
									return 1;
								}
							} else {
								if(m1.getDesempate() != null) {
									return -1;
								} else {
									return 1;
								}
							}
						}
					}
				}
				return 0;
			}

		});

		return monitorias;
	}
	
	public List<Monitoria> classificar(List<Monitoria> monitorias) {
		int classificacao = 1;
		int vagasVoluntario = monitorias.get(0).getPlanoMonitoria().getVoluntarios();
		int vagasBolsista = monitorias.get(0).getPlanoMonitoria().getBolsas();
		
		for (Monitoria monitoria : monitorias) {
			System.out.println(monitoria.getAluno().getNome());
			if(monitoria.isClassificado()) {
				monitoria.setClassificacao(classificacao);
				classificacao++;
				
				if(monitoria.isBolsista()) {
					if(vagasBolsista > 0) {
						monitoria.setSelecionado(true);
						vagasBolsista--;
					} else {
						monitoria.setSelecionado(false);
					}
				} else {
					if(vagasVoluntario > 0) {
						monitoria.setSelecionado(true);
						vagasVoluntario--;
					} else {
						monitoria.setSelecionado(false);
					}
				}
			}
			else {
				monitoria.setClassificacao(null);
				monitoria.setSelecionado(false);
			}
		}
		return monitorias;
	}

	public void alterarPosicaoDoEmpate(Monitoria monitoria, boolean subir, List<Monitoria> monitorias) {
		 List<Monitoria> empatados = getEmpatados(monitoria, monitorias);
		 int notaAnterior = monitoria.getDesempate();
		 int notaMaxima = empatados.size();
		 int novaNota;
		 if(subir) {
			 if(notaAnterior + 1 <= notaMaxima) {
				 novaNota = notaAnterior + 1;
			 } else {
				 novaNota = notaAnterior;
			 }
		 } else {
			 if(notaAnterior - 1 > 0) {
				 novaNota = notaAnterior - 1;
			 } else {
				 novaNota = notaAnterior;
			 }
		 }
		 
		 if(novaNota != notaAnterior) {
			for (Monitoria m1 : monitorias) {
				if(empatados.contains(m1)) {
					if(m1.getDesempate() == novaNota) {
						m1.setDesempate(notaAnterior);
					}
					else if(m1.getId() == monitoria.getId()) {
						m1.setDesempate(novaNota);
					}
				}
			}
		 }
		 
		 salvarNotas(monitorias);
	}

	private List<Monitoria> getEmpatados(Monitoria monitoria, List<Monitoria> monitorias) {
		List<Monitoria> empatados = new ArrayList<>();
		for (Monitoria m1 : monitorias) {
			if(m1.isEmpatado()) {
				if(monitoria.getMediaComponente().doubleValue() == m1.getMediaComponente().doubleValue() &&
						monitoria.getNotaSelecao().doubleValue() == m1.getNotaSelecao().doubleValue()) {
					empatados.add(m1);
				}
			}
		}
		return empatados;
	}
}
