use testdb;
SELECT utente as Utente, anno AS  Anno, SUM(vendite) AS VenditeTot, 
                SUM(vendite) -(
                    SELECT SUM(vendite)
                    FROM testDB.info_vendite AS info_vendite_2
                    WHERE info_vendite_2.utente = info_vendite_1.utente AND info_vendite_2.anno = info_vendite_1.anno - 1
                ) AS differenza
FROM testDB.info_vendite AS info_vendite_1
GROUP BY utente, anno
ORDER BY utente, anno
