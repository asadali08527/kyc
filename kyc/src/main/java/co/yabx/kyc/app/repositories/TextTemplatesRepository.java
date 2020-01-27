package co.yabx.kyc.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.yabx.kyc.app.entities.TextTemplates;

@Repository("textTemplatesRepository")
public interface TextTemplatesRepository extends JpaRepository<TextTemplates, Long> {
	TextTemplates findByTemplateName(String templateName);

	@Query("select textTemplate from TextTemplates textTemplate where locale=?1 and templateName=?2")
	TextTemplates findByLocale(String locale, String templateName);

	@Query("select textTemplate from TextTemplates textTemplate where locale=?1 ")
	List<TextTemplates> findByLocale(String locale);
}
