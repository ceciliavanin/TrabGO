package br.univel.trabalho;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.enums.EstadoCivil;
import br.univel.trabalho.Cliente;
import br.univel.annotations.Coluna;
import br.univel.annotations.Tabela;
import br.univel.annotations.*;

public class Montando extends SQLGen {
	

	@Override
	protected String getCreateTable(Object obj) throws SQLException {
		Class<? extends Object> cl = obj.getClass();
		try {

			StringBuilder sb = new StringBuilder();
			
			{
				String nomeTabela;
				if (cl.isAnnotationPresent(Tabela.class)) {

					Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
					nomeTabela = anotacaoTabela.value();

				} else {
					nomeTabela = cl.getSimpleName().toUpperCase();

				}
				sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
			}

			Field[] atributos = cl.getDeclaredFields();

			{
				for (int i = 0; i < atributos.length; i++) {

					Field field = atributos[i];

					String nomeColuna;
					String tipoColuna = null;
					int tamanhoColuna;
					Coluna anotacaoColuna = null;
					if (field.isAnnotationPresent(Coluna.class)) {
						anotacaoColuna = field.getAnnotation(Coluna.class);

						if (anotacaoColuna.nome().isEmpty()) {
							nomeColuna = field.getName().toUpperCase();
						} else {
							nomeColuna = anotacaoColuna.nome();
						}

					} else {
						nomeColuna = field.getName().toUpperCase();
					}

					Class<?> tipoParametro = field.getType();

					if (tipoParametro.equals(String.class)) {
							tamanhoColuna = anotacaoColuna.tamanho();
							tipoColuna = "VARCHAR("+tamanhoColuna+")";
						
					}else if (tipoParametro.equals(int.class)) {
						tipoColuna = "INT";

					}else if (tipoParametro.equals(EstadoCivil.class)) {
						tipoColuna = "VARCHAR(20)";
					}
					
					else {
						tipoColuna = "DESCONHECIDO";
					}

					if (i > 0) {
						sb.append(",");
					}

					sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
				}
			}

			{

				sb.append(",\n\tPRIMARY KEY( ");

				for (int i = 0, achou = 0; i < atributos.length; i++) {

					Field field = atributos[i];
					if (field.isAnnotationPresent(Coluna.class)) {

						Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

						if (anotacaoColuna.pk()) {

							if (achou > 0) {
								sb.append(", ");
							}

							if (anotacaoColuna.nome().isEmpty()) {
								sb.append(field.getName().toUpperCase());
							} else {
								sb.append(anotacaoColuna.nome());
							}

							achou++;
						}

					}
				}

				sb.append(" )");
			}

			sb.append("\n);");
			System.out.println(sb.toString());
			return sb.toString();

		} catch (SecurityException e) {
			throw new RuntimeException(e);
			
		}
	
	}

	@Override
	protected String getDropTable(Object obj) {
		Class<? extends Object> cl = obj.getClass();
		
		try {

			StringBuilder sb = new StringBuilder();
			
				String nomeTabela;
				if (cl.isAnnotationPresent(Tabela.class)) {

					Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
					nomeTabela = anotacaoTabela.value();

				} else {
					nomeTabela = cl.getSimpleName().toUpperCase();

				}
				sb.append("DROP TABLE ").append(nomeTabela).append(";");
			System.out.println(sb.toString());
			return sb.toString();
	} catch (SecurityException e) {
		throw new RuntimeException(e);
	}
		
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {

		Class<? extends Object> cl = obj.getClass();

		StringBuilder sb = new StringBuilder();

		{
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("INSERT INTO ").append(nomeTabela).append(" (");
		}

		Field[] atributos = cl.getDeclaredFields();

		{
			for (int i = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				String nomeColuna;

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = anotacaoColuna.nome();
					}

				} else {
					nomeColuna = field.getName().toUpperCase();
				}

				if (i > 0) {
					sb.append(", ");
				}

				sb.append(nomeColuna);
			}
		}

		sb.append(") VALUES (");

		for (int i = 0; i < atributos.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append('?');
		}
		sb.append(')');

		String strSql = sb.toString();
		
		try {
			PreparedStatement ps = con.prepareStatement(strSql);
			int res = ps.executeUpdate();

			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];

				field.setAccessible(true);

				if (field.getType().equals(int.class)) {
					ps.setInt(i + 1, field.getInt(obj));

				} else if (field.getType().equals(String.class)) {
					ps.setString(i + 1, String.valueOf(field.get(obj)));

				} else if (field.getType().equals(EstadoCivil.class)) {
					ps.setString(i + 1, String.valueOf(field.get(obj)));
				} else {
					throw new RuntimeException("Tipo não suportado, falta implementar.");

				}
			}
			System.out.println(ps);
			return ps;


		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;

		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;

		}
		
	}


	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
		Class<? extends Object> cl = obj.getClass();

		StringBuilder sb = new StringBuilder();
		
		{
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("SELECT * FROM ").append(nomeTabela).append(";");
		}

		String strSql = sb.toString();

		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(strSql);
			int res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return ps;

	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj, int id ){

			Class<? extends Object> cl = obj.getClass();

			StringBuilder sb = new StringBuilder();

			{
				String nomeTabela;
				if (cl.isAnnotationPresent(Tabela.class)) {

					Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
					nomeTabela = anotacaoTabela.value();

				} else {
					nomeTabela = cl.getSimpleName().toUpperCase();

				}
				
				sb.append("SELECT * FROM ").append(nomeTabela).append(" WHERE ");
			}
			Field[] atributos = cl.getDeclaredFields();

			for (int i = 0, achou = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				if (field.isAnnotationPresent(Coluna.class)) {

					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.pk()) {

						if (achou > 0) {
							sb.append(", ");
						}

						if (anotacaoColuna.nome().isEmpty()) {
							sb.append(field.getName().toUpperCase());
						} else {
							sb.append(anotacaoColuna.nome());
						}

						achou++;
					}

				}
			}
			id = 1;
			sb.append(" = ").append(id);
			String strSql = sb.toString();

			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(strSql);
				int res = ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			return ps;	
		}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj, int id) {

        Class<?> c = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nometabela;

        if (c.isAnnotationPresent(Tabela.class)) {
            nometabela = c.getAnnotation(Tabela.class).value();
        } else {
            nometabela = c.getSimpleName().toUpperCase();
        }

        sb.append("UPDATE ").append(nometabela).append(" SET ");

        Field[] atributos = c.getDeclaredFields();

        for (int i = 0; i < atributos.length; i++) {
            Field field = atributos[i];
            String nomecoluna;

            if (field.isAnnotationPresent(Coluna.class)) {
                Coluna coluna = field.getAnnotation(Coluna.class);
                if (coluna.nome().isEmpty()) {
                    nomecoluna = field.getName().toUpperCase();
                } else {
                    nomecoluna = coluna.nome();
                }
            } else {
                nomecoluna = field.getName().toUpperCase();
            }

            if (i > 0) {
                sb.append(", ");
            }

            sb.append(nomecoluna).append(" = ?");
        }
        sb.append(" WHERE ID = ").append(id);
        String update = sb.toString();
        System.out.println(update);

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(update);
    		int res = ps.executeUpdate();

            for (int i = 0; i < atributos.length; i++) {
                Field field = atributos[i];
                Object type = field.getType();

                field.setAccessible(true);
                if (type.equals(int.class)) {
                    ps.setInt(i + 1, field.getInt(obj));
                } else if (type.equals(String.class)) {
                    ps.setString(i + 1, String.valueOf(field.get(obj)));
                } else if (field.getType().isEnum()) {
                    Object value = field.get(obj);
                    Method m = value.getClass().getMethod("ordinal");
                    ps.setInt(i + 1, (Integer) m.invoke(value, null));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ps;
    }

	
	@Override
    protected PreparedStatement getSqlDeleteById(Connection con, Object obj, int id) {
        PreparedStatement ps = null;
        try {
            Class<?> cl = obj.getClass();
            StringBuilder sb = new StringBuilder();
            String nometabela;

            if (cl.isAnnotationPresent(Tabela.class)) {
                nometabela = cl.getAnnotation(Tabela.class).value();
            } else {
                nometabela = cl.getSimpleName().toUpperCase();
            }

            sb.append("DELETE FROM ").append(nometabela).append(" WHERE ID = ").append(id).append(";");
            String exc = sb.toString();
            System.out.println(exc);

            ps = con.prepareStatement(exc);
    		int res = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

	public static void main(String[] args) {
		new Montando();
	}



	@Override
	protected String getCreateTable(Class<Cliente> cl) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected String getDropTable(Class<Cliente> cl) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected PreparedStatement getSqlDeleteById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	

	public Connection Conexao() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
