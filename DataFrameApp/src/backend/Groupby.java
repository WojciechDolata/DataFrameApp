package backend;

interface Groupby{
    DataFrame max() throws DataFrameException;
    DataFrame min() throws DataFrameException;
    DataFrame mean() throws DataFrameException;
    DataFrame std() throws DataFrameException;
    DataFrame sum() throws DataFrameException ;
    DataFrame var() throws DataFrameException;
    DataFrame apply(Applyable a);
}