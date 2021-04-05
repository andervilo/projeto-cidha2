import React, { useState, useEffect, useMemo} from 'react';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Button, InputGroup} from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const Filter = ({
        onSubmit,
        btnOnClickClear,
        setQuery,
        filter = "",
        placeholder = "",
        isForReset
    }) => {
        const [search, setSearch] = useState('');
        const handleSearch = event =>{
            setSearch(event.target.value);
            setQuery(`${filter}=${event.target.value}`);
        }

        const clearFilter = () =>{
            
            btnOnClickClear();
        }

        useEffect(() => {
        //   return () => {
            setSearch('');
            console.log(filter+":"+isForReset);
        //   };
        }, [isForReset])

        
    return (
        <AvForm onSubmit={onSubmit}>
            <AvGroup>
                <InputGroup>
                    <AvInput
                    type="text"
                    name="search"
                    value={search}
                    onChange={handleSearch}
                    placeholder={placeholder}
                    />
                    <Button size="sm" className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                    </Button>
                    <Button size="sm" type="reset" color="danger" className="input-group-addon" onClick={clearFilter}>
                    <FontAwesomeIcon icon="trash" />
                    </Button>
                </InputGroup>
            </AvGroup>
        </AvForm>
    );
}

export default Filter;