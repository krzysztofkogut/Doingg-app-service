import React from 'react';

const SearchBox = ({ searchfield, searchChange }) => {
	return (
		<div className='searchHeader'>
			<input type='search' placeholder='Search' onChange={searchChange} />
		</div>
	);
};
export default SearchBox;